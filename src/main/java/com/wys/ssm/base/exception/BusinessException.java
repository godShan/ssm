package com.wys.ssm.base.exception;

import com.wys.ssm.base.model.Result;


/**
 * 
 * <pre>
 * 订单业务异常
 * </pre>
 *
 * @author penglei
 * @version $Id: BusinessException.java, v 0.1 2014-6-7 上午11:42:50 Exp $
 */
public class BusinessException extends BaseException {

    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = -4987001305340759909L;

    private BusinessException() {
        super();
    }

    private BusinessException(String errCode) {
        super(errCode);
    }

    private BusinessException(String errCode, String... params) {
        super(errCode, params); // 此处
    }

    private BusinessException(String errCode, String message) {
        super(errCode, message);
    }

    /**
     * 
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     *
     * @param msg 异常信息
     */
    public static void throwMessage(String message) {
        throw new BusinessException(Result.INPUT.toString(), message);
    }

    /**
     * 
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     *
     * @param msg 异常信息
     */
     public static void throwMessageWithCode(String errCode, String message) {
         throw new BusinessException(errCode, message);
     }


}
