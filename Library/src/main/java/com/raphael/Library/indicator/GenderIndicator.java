package com.raphael.Library.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum GenderIndicator {
    ROMANCE(Arrays.asList("romance", "drama")),
    HORROR(Arrays.asList("horror")),
    SCIENCE(Arrays.asList("ciência", "ficção")),
    TERROR(Arrays.asList("terror")),
    THRILLER(Arrays.asList("suspense", "tensão")),
    FINANCE(Arrays.asList("finanças", "economia")),
    SELFHELP(Arrays.asList("auto-ajuda")),
    PHILOSOPHY(Arrays.asList("filosofia")),
    HISTORY(Arrays.asList("historia")),
    EDUCATION(Arrays.asList("educação")),
    TECH(Arrays.asList("tecnologia", "informática")),
    HEALTH(Arrays.asList("saúde", "bem-estar")),
    POLITIC(Arrays.asList("politica")),
    SPORTS(Arrays.asList("esporte")),
    KIDS(Arrays.asList("infantil", "crianças"));

    private List<String> names;
}
