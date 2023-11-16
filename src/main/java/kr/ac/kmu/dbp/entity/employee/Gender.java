package kr.ac.kmu.dbp.entity.employee;

public enum Gender {
    남성, 여성;

    public static Gender getRandom() {
        return values()[(int) (Math.random()*values().length)];
    }
}
