    public static <T> Set<T> getInstancesOf(Injector injector, Class<T> baseClass) {
        Set<T> answer = Sets.newHashSet();
        Set<Entry<Key<?>, Binding<?>>> entries = injector.getBindings().entrySet();
        for (Entry<Key<?>, Binding<?>> entry : entries) {
            Key<?> key = entry.getKey();
            Class<?> keyType = getKeyType(key);
            if (keyType != null && baseClass.isAssignableFrom(keyType)) {
                Binding<?> binding = entry.getValue();
                Object value = binding.getProvider().get();
                if (value != null) {
                    T castValue = baseClass.cast(value);
                    answer.add(castValue);
                }
            }
        }
        return answer;
    }
