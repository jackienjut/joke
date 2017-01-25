package com.example.hailu.joke.model;

/**
 * Created by hailu on 2016/6/3.
 */
public class JokeText {
    public int show_api_res_code;
    public String show_api_res_error;
    public JokeTextBody body;

    public int getShow_api_res_code() {
        return show_api_res_code;
    }

    public void setShow_api_res_code(int show_api_res_code) {
        this.show_api_res_code = show_api_res_code;
    }

    public String getShow_api_res_error() {
        return show_api_res_error;
    }

    public void setShow_api_res_error(String show_api_res_error) {
        this.show_api_res_error = show_api_res_error;
    }

    public JokeTextBody getBody() {
        return body;
    }

    public void setBody(JokeTextBody body) {
        this.body = body;
    }
}
