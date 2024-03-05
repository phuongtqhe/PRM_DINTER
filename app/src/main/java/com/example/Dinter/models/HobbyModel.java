package com.example.Dinter.models;

import java.util.List;

public class HobbyModel {

    public static class JsonResponse {
        private List<HobbyModel> data;
        private String message;

        public JsonResponse(List<HobbyModel> data, String message) {
            this.data = data;
            this.message = message;
        }

        public JsonResponse() {
        }

        public List<HobbyModel> getData() {
            return data;
        }

        public void setData(List<HobbyModel> data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private String _id;
    private String hobbyName;

    public HobbyModel(String _id, String hobbyName) {
        this._id = _id;
        this.hobbyName = hobbyName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }


}
