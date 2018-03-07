package com.yhj.chapter19;

public class TableDriveCode {
    public enum Table {
        ROW {
            void detail() {
                System.out.println("This is Row");
            }
        },
        COL {
            void detail() {
                System.out.println("This is Col");
            }
        };
       void detail(){

       }
    }

    public static void main(String[] args) {
        for (Table t : Table.values()) {
            t.detail();
        }
    }
}
