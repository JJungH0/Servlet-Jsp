package com.nhnacademy.chap02;

import lombok.Getter;

@Getter
public class Member {
    public enum Role{
        ADMIN, USER, MANAGER, NONE
    }

    private final String id;
    private final String name;
    private final String password;
    private final Role role;

    public Member(String id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public static Member createAdmin(String id, String name, String password) {
        return new Member(id, name, password, Role.ADMIN);
    }

    public static Member createUser(String id, String name, String password) {
        return new Member(id, name, password, Role.USER);
    }

    public static Member createManger(String id, String name, String password) {
        return new Member(id, name, password, Role.MANAGER);
    }

    public static Member createUncertifiedMember(String id, String name, String password) {
        return new Member(id, name, password, Role.NONE);
    }

    /**
     * 1. Enum은 JVM이 각 enum 상수를 클래스 로더당 단 하나의 객체(=싱글톤)
     *  으로 보장을 하기 때문에 동등성 비교보다는 동일성 비교가 맞다.
     * 2. equals로 비교시 null이 넘어오면 NPE이 터지기 떄문에 따로 예외처리를 해줘야한다.
     * @param role
     * @return
     */
    public boolean hasRole(Role role) {
        return this.role == role;
    }
}
