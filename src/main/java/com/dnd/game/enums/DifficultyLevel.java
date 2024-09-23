package com.dnd.game.enums;

public enum DifficultyLevel {
    NOTENOUGHINFO(0, "Not Enough Info"),
    BEGINNER(1, "Beginner"),
    INTERMEDIATE(2, "Intermediate"),
    ADVANCED(3, "Advanced"),
    EXPERT(4, "Expert"),
    MASTER(5, "Master");


    private final int level;
    private final String desc;

    DifficultyLevel(int level, String desc) {
        this.level = level;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static DifficultyLevel fromLevel(int level) {
        for (DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
            if(difficultyLevel.level == level) {
                return difficultyLevel;
            }
        }
        throw new IllegalArgumentException("Invalid level: " + level);
    }
}
