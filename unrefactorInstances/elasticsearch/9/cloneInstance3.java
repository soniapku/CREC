    public String toString() {
        String sSource = "_na_";
        try {
            sSource = XContentHelper.convertToJson(searchRequest.source(), false);
        } catch (IOException e) {
            // ignore
        }
        return "[" + Arrays.toString(indices) + "]" + Arrays.toString(types()) + ", source[" + sSource + "]";
    }
