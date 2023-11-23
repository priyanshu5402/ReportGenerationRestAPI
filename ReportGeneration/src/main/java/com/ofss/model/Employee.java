package com.ofss.model;

 public class Employee {
        private String id;
        private String name;
        private String deptId;
        private String deptName;

        // Constructors
        public Employee() {
        }

        public Employee(String id, String name, String deptId, String deptName) {
            this.id = id;
            this.name = name;
            this.deptId = deptId;
            this.deptName = deptName;
        }

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        // toString method
        @Override
        public String toString() {
            return "Employee{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", deptId='" + deptId + '\'' +
                    ", deptName='" + deptName + '\'' +
                    '}';
        }
    }