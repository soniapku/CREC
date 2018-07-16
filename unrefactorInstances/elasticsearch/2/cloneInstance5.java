    public static Set<Binding<?>> getBindingsOf(Injector injector, Matcher<Class> matcher) {
        Set<Binding<?>> answer = Sets.newHashSet();
        Set<Entry<Key<?>, Binding<?>>> entries = injector.getBindings().entrySet();
        for (Entry<Key<?>, Binding<?>> entry : entries) {
            Key<?> key = entry.getKey();
            Class<?> keyType = getKeyType(key);
            if (keyType != null && matcher.matches(keyType)) {
                answer.add(entry.getValue());
            }
        }
        return answer;
    }
