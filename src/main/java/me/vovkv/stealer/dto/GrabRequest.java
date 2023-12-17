package me.vovkv.stealer.dto;

import jakarta.validation.constraints.Min;

public record GrabRequest(@Min(value = 0, message = "Sol should not be less than 0") Integer sol) {}
