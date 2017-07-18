package com.jc.hystrix;

import com.jc.model.Employee;
import com.netflix.hystrix.*;

import java.util.function.Function;

/**
 * 隔离及服务降级熔断
 * 如果是调用远程服务 则这个做法是有用的
 * Created by jasonzhu on 2017/7/16.
 */
public class GetEmployeeCommand extends HystrixCommand<Employee> {
    private Function<String, Employee> function;
    private String record;

    /**
     * 初始化
     *
     * @param function 方法
     * @param record   搜索条件
     */
    public GetEmployeeCommand(Function<String, Employee> function, String record) {
        super(threadSetter());
        this.function = function;
        this.record = record;
    }

    /**
     * 线程隔离配置
     */
    private static Setter threadSetter() {
        //服务分组 全局唯一服务分组名，相同分组的服务会聚合在一起
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("employee");
        //服务标识 全局唯一标识服务名 默认简单类名
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("getEmployee");
        //线程池名称 全局唯一线程池名 默认分组名
        HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("employee-pool");
        //线程池配置
        HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter()
                //核心线程池大小
                .withCoreSize(10)
                //空闲线程生存时间
                .withKeepAliveTimeMinutes(5)
                //线程池队列最大大小
                .withMaxQueueSize(Integer.MAX_VALUE)
                //当前队列大小
                .withQueueSizeRejectionThreshold(1000);
        //命令属性设置
        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                //隔离策略
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)

                //是否启用降级 默认true
                .withFallbackEnabled(true)
                //超过信号量限制，不再调用getFallback 快速失败
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(10)
                //当执行线程超时时 是否进行中断处理 默认true
                .withExecutionIsolationThreadInterruptOnTimeout(true)
                //是否启用超时机制 默认true
                .withExecutionTimeoutEnabled(true)
                //执行超时时间 默认1000ms
                .withExecutionTimeoutInMilliseconds(1000)

                //是否开启熔断机制 默认true
                .withCircuitBreakerEnabled(true)
                //是否强制关闭熔断 关闭了则不会降级 默认false
                .withCircuitBreakerForceClosed(false)
                //是否强制打开熔断 打开了强制降级 默认false 可根据需求进行配置文件配置
                .withCircuitBreakerForceOpen(false)
                //采样窗口内失败率 超出则自动打开降级开关 默认10秒50%失败率
                .withCircuitBreakerErrorThresholdPercentage(50)
                //开关闭合情况下 失败率判断之前一个采样周期必须进行N个请求 默认20
                .withCircuitBreakerRequestVolumeThreshold(20)
                //熔断后重试窗口 只允许一次重试 默认5000ms
                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                ;

        return HystrixCommand.Setter.withGroupKey(groupKey)
                .andCommandKey(commandKey)
                .andThreadPoolKey(threadPoolKey)
                .andThreadPoolPropertiesDefaults(threadPoolProperties)
                .andCommandPropertiesDefaults(commandProperties);
    }

    /**
     * 信号量隔离配置
     * 只限制了总的并发数 同步调用 没有线程池
     */
    private static Setter semaphoreSetter(){
        //服务分组 全局唯一服务分组名，相同分组的服务会聚合在一起
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("employee");
        //服务标识 全局唯一标识服务名 默认简单类名
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("getEmployee");
        HystrixCommandProperties.Setter commandProperties =  HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(50);
        return HystrixCommand.Setter.withGroupKey(groupKey)
                .andCommandPropertiesDefaults(commandProperties);
    }
    @Override
    protected Employee run() throws Exception {
        return function.apply(record);
    }

    //降级方法
    @Override
    protected Employee getFallback() {
        Employee employee = new Employee();
        employee.setId(-1);
        return employee;
    }

}
