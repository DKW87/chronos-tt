package com.github.dkw87.chronostt.repository.memory;

public class MemoryRepository {

    public static MemoryRepository getInstance() {
        return MemoryRepository.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MemoryRepository INSTANCE = new MemoryRepository();
    }
    
}
