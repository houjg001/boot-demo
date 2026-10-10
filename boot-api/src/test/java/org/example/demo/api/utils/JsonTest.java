package org.example.demo.api.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.demo.api.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class JsonTest {

    @Test
    public void toJson() {
        Gson gson = GsonUtils.buildGson();
        UserDto userDto = new UserDto();
        userDto.setBirthday(LocalDate.now());
        System.out.println(gson.toJson(userDto));
    }

    @Test
    public void fromJson() {
        Gson gson = GsonUtils.buildGson();
        String userJson = """
                [{
                    "userName": "刘五哥",
                    "birthday": "1989-04-19",
                    "email": "lwg@sohu.com",
                    "addresses": [
                        {
                            "addr": "开发区和平路3号魔法小区4号楼2单元1109",
                            "postcode": "310102"
                        },
                        {
                            "addr": "dddd",
                            "postcode": "310003"
                        }
                    ]
                }]
                """;
//        List<UserDto> dto = JsonUtils.fromJson(userJson, List.class);
        List<UserDto> dto = gson.fromJson(userJson, new TypeToken<List<UserDto>>(){});
        System.out.println(dto.get(0).getUserName());
    }
}
