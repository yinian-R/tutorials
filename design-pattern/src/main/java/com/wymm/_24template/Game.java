package com.wymm._24template;

public abstract class Game {
    abstract void initialize();
    
    abstract void startPlay();
    
    abstract void endPlay();
    
    
    public void play() {
        // 初始化游戏
        initialize();
        // 开始游戏
        startPlay();
        // 结束游戏
        endPlay();
    }
}
