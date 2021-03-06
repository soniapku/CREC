    public void testDispatchWithURLAndSOAPAction() throws Exception {
        SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
        OMNamespace omNs = fac.createOMNamespace("http://dummyURL", "my");
        OMElement payload = fac.createOMElement("echoOMElementRequest", omNs);
        OMElement value = fac.createOMElement("myValue", omNs);
        value.addChild(
                fac.createOMText(value, "Isaac Asimov, The Foundation Trilogy"));
        payload.addChild(value);
        Options options = new Options();
        options.setTo(
                new EndpointReference("http://127.0.0.1:5555/axis2/services/EchoXMLService/"));
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setAction("echoOMElement");
        ConfigurationContext configContext =
                ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
        ServiceClient sender = new ServiceClient(configContext, null);
        sender.setOptions(options);

        OMElement result = sender.sendReceive(payload);
        TestingUtils.compareWithCreatedOMElement(result);
    }
