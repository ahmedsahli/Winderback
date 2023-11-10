package com.example.pidev.Service;

import com.example.pidev.entity.Option;

import java.util.List;

public interface OptionService {
    List<Option> getAllOptions();
    Option getOptionById(Long option_id);
    Option createOption(Option option);
    Option updateOption(Option option);
    void deleteOption(Long option_id);
}
