    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeBoolean(filterCache);
        out.writeBoolean(fieldDataCache);
        out.writeBoolean(idCache);
        if (fields == null) {
            out.writeVInt(0);
        } else {
            out.writeVInt(fields.length);
            for (String field : fields) {
                out.writeString(field);
            }
        }
    }
