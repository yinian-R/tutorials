package com.wymm._6adapter.media.player;

public class AudioPlayer implements MediaPlayer {
    
    MediaAdapter mediaAdapter;
    
    /**
     * 原本就支持mp3，拓展支持其他播放格式
     *
     * @param audioType 播放类型
     * @param fileName  播放文件名称
     */
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file. Name: " + fileName);
        } else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
            // mediaAdapter 提供了播放其他文件格式的支持
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}
