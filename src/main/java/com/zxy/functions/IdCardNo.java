package com.zxy.functions;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import com.zxy.jmeter.Tool;
import com.zxy.jmeter.IdCard;

/**
 * @author 醉逍遥
 * @version v1.0
 */
public class IdCardNo extends AbstractFunction {
    /** 函数描述 */
    private static final List<String> desc = new LinkedList<String>();
    static {
        desc.add("地区 (例: 上海市)");
    }

    /** 函数名称 */
    private static final String FUNCTION_NAME = "__IdCard";

    /** 传入参数的最小数量 */
    private static final int MIN_PARA_COUNT = 0;
    /** 传入参数的最大数量 */
    private static final int MAX_PARA_COUNT = 1;

    /** 函数接收值 */
    private Object[] values;

    /**
     * 函数算法
     * @return 函数运算结果
     */
    private String run() {
        if (values.length == 1) {
            String val1 = new String(((CompoundVariable) values[0]).execute().trim());
            if (!(val1 == null || val1.length() <= 0)) {
                return IdCard.getIdCard(val1);
            }else {
                return IdCard.getIdCard();
            }
        }else {
            return IdCard.getIdCard();
        }
    }

    /**
     * 返回参数描述
     */
    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    /**
     * 返回函数运行结果
     */
    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {
        try {
            return run();
        } catch (Exception ex) {
            throw new InvalidVariableException(ex);
        }
    }

    /**
     * 返回函数名
     */
    @Override
    public String getReferenceKey() {
        return FUNCTION_NAME;
    }

    /**
     * 返回输入参数
     */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, MIN_PARA_COUNT, MAX_PARA_COUNT); // 检查参数的个数是否正确
        values = parameters.toArray(); // 将值存入类变量中
    }

}