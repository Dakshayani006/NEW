package com.scholastic.intl.esb.integration.wsdl.palm;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-06-02T10:33:16.708+05:30
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "JadeNetsuiteOrgProvider", 
                  wsdlLocation = "https://webservices.sandbox.netsuite.com/wsdl/v2015_1_0/netsuite.wsdl",
                  targetNamespace = "urn:JadeWebServices/NetsuiteCustomer/") 
public class JadeNetsuiteOrgProvider extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:JadeWebServices/NetsuiteCustomer/", "JadeNetsuiteOrgProvider");
    public final static QName JadeNetsuiteOrgProviderSoap = new QName("urn:JadeWebServices/NetsuiteCustomer/", "JadeNetsuiteOrgProviderSoap");
    static {
        URL url = null;
        try {
            url = new URL("https://webservices.sandbox.netsuite.com/wsdl/v2015_1_0/netsuite.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(JadeNetsuiteOrgProvider.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "https://webservices.sandbox.netsuite.com/wsdl/v2015_1_0/netsuite.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public JadeNetsuiteOrgProvider(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public JadeNetsuiteOrgProvider(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public JadeNetsuiteOrgProvider() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public JadeNetsuiteOrgProvider(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public JadeNetsuiteOrgProvider(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public JadeNetsuiteOrgProvider(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns JadeNetsuiteOrgProviderSoap
     */
    @WebEndpoint(name = "JadeNetsuiteOrgProviderSoap")
    public JadeNetsuiteOrgProviderSoap getJadeNetsuiteOrgProviderSoap() {
        return super.getPort(JadeNetsuiteOrgProviderSoap, JadeNetsuiteOrgProviderSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns JadeNetsuiteOrgProviderSoap
     */
    @WebEndpoint(name = "JadeNetsuiteOrgProviderSoap")
    public JadeNetsuiteOrgProviderSoap getJadeNetsuiteOrgProviderSoap(WebServiceFeature... features) {
        return super.getPort(JadeNetsuiteOrgProviderSoap, JadeNetsuiteOrgProviderSoap.class, features);
    }

}
