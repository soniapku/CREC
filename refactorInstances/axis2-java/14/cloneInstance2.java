    public void testDispatchWithSOAPBody() throws Exception {
        SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();

        OMNamespace omNs = fac.createOMNamespace(
                "http://127.0.0.1:5555/axis2/services/EchoXMLService", "my");
        OMElement payload = fac.createOMElement("echoOMElement", omNs);
        OMElement value = fac.createOMElement("myValue", omNs);
        value.addChild(
                fac.createOMText(value, "Isaac Asimov, The Foundation Trilogy"));
        payload.addChild(value);
        Options options = new Options();
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        ConfigurationContext configContext =
                ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
        ServiceClient sender = new ServiceClient(configContext, null);
        sender.setOptions(options);
        options.setTo(targetEPR);

        OMElement result = sender.sendReceive(payload);

        TestingUtils.compareWithCreatedOMElement(result);
    }
