package cn.com.u2be.framework.proxy;

/**
 * Created by �� on 2015/11/23.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
