package com.pz.game.test;

import java.util.Objects;

public class Result {
    private final boolean success;
    private final int levels;

    public static Result success(int successLevels){
        return new Result(true, successLevels);
    }

    public static Result failure(int successLevels){
        return new Result(false, successLevels);
    }

    public Result(boolean success, int levels) {
        this.success = success;
        this.levels = levels;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getLevels() {
        return levels;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", levels=" + levels +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return success == result.success &&
                levels == result.levels;
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, levels);
    }
}
