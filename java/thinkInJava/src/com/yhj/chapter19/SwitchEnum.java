package com.yhj.chapter19;

public class SwitchEnum {
    Shrubbery s = Shrubbery.GROUND;

    private void switchChange() {
        switch (s) {
            case GROUND:
                s = Shrubbery.LOWER;
                break;
            case LOWER:
                s = Shrubbery.UPPER;
                break;
            case UPPER:
                s = Shrubbery.GROUND;
                break;
        }
    }

    public static void main(String[] args) {
        SwitchEnum switchEnum = new SwitchEnum();
        for (int i = 0; i < 3; i++) {
            System.out.println(switchEnum);
            switchEnum.switchChange();
        }
    }

    @Override
    public String toString() {
        return "show time " + s;
    }
}
