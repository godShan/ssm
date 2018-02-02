package com.wys.ssm.base.model;

public class CtxSessionBag {

    private static final ThreadLocal<SessionBagImpl> sessionBagThread = new ThreadLocal<SessionBagImpl>();

    /**
     * Description ：获取线程局部变量
     * 
     * nyj
     * 
     * @return
     * @since
     *
     */
    public static SessionBagImpl getSessionBag() {
        return sessionBagThread.get();
    }

    /**
     * Description ： 设置线程局部变量
     * 
     * nyj
     * 
     * @param sessionBagImpl
     * @since
     *
     */
    public static void setSessionBag(SessionBagImpl sessionBagImpl) {
        sessionBagThread.set(sessionBagImpl);
    }

    /**
     * 线程内清除
     */
    public static void clear() {
        sessionBagThread.remove();;
    }

}
