    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EmptyMemberValueSource other = (EmptyMemberValueSource) obj;
        if (!fieldData.equals(other.fieldData)) return false;
        return true;
    }
