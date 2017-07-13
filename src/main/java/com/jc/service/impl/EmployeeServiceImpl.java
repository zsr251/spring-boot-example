package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.jc.constant.DepartmentEnum;
import com.jc.exception.ApplyException;
import com.jc.mapper.EmployeeMapper;
import com.jc.model.Employee;
import com.jc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jasonzhu on 2017/7/13.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee addEmployee(DepartmentEnum de, String realName, String englishName) {
        Preconditions.checkNotNull(de, "部门不能为空");
        Preconditions.checkNotNull(realName, "真是姓名不能为空");
        Preconditions.checkNotNull(englishName, "英文名不能为空");
        Employee record = getEmployee(englishName);
        if (record != null)
            throw new ApplyException(MessageFormat.format("英文名已存在 ID【{0}】部门【{1}】姓名【{2}】英文名【{3}】", record.getId(), record.getDepartment(), record.getRealName(), record.getEnglishName()));
        record.setDepartment(de.name());
        record.setRealName(realName);
        record.setEnglishName(englishName);
        record.setCreateTime(new Date());
        employeeMapper.insertUseGeneratedKeys(record);
        return record;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        Preconditions.checkNotNull(employee, "参数不能为空");
        Preconditions.checkNotNull(employee.getId(), "员工ID不能为空");
        return employeeMapper.updateByPrimaryKeySelective(employee) > 0;
    }

    @Override
    public PageInfo<Employee> selectEmployee(Employee record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = employeeMapper.select(record);
        return new PageInfo<>(list);
    }

    @Override
    public Employee getEmployee(String englishName) {
        Preconditions.checkNotNull(englishName, "英文名不能为空");
        Employee record = new Employee();
        record.setEnglishName(englishName);
        List<Employee> list = employeeMapper.select(record);
        if (list == null || list.size() < 1)
            return null;
        return list.get(0);
    }

}
