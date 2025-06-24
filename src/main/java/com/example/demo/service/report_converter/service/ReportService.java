package com.example.demo.service.report_converter.service;

import com.example.demo.client.dto.PokemonAbilityInfo;
import com.example.demo.client.dto.PokemonApiResponse;
import com.example.demo.model.db.Report;
import com.example.demo.service.report_converter.model.MappedField;
import com.example.demo.service.report_converter.model.ReportField;
import com.example.demo.service.report_converter.model.ReportLine;
import com.example.demo.service.report_converter.model.ReportSection;
import com.example.demo.service.report_converter.model.StaticField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportService {
    public static List<String> convertToReport(PokemonApiResponse pokemonApiResponse) {

        ReportLine<PokemonApiResponse> line1 = new ReportLine<>("nameLine");
        line1.addField(new ReportField<>("Name", new MappedField<>(PokemonApiResponse::getName), 0));
        line1.addField(new ReportField<>("E", new MappedField<>(p -> p.getTypes().get(0).getType().getName()), 30));

        ReportLine<PokemonApiResponse> line2 = new ReportLine<>("surnameLine");
        line2.addField(new ReportField<>("Surname", new MappedField<>(p -> p.getAbilities().get(0).getAbility().getName()), 0));
        line2.addField(new ReportField<>("RSDF", new StaticField<>(pokemonApiResponse.getHeight() + " TO " + pokemonApiResponse.getHeight()), 30));
        int section1Separator = 50;
        ReportSection<PokemonApiResponse> section1 = new ReportSection<>("DATA REPORT");
        int count = 0;
        for (PokemonAbilityInfo slot : pokemonApiResponse.getAbilities()) {
            ReportLine<PokemonApiResponse> abilityNameLine = new ReportLine<>("abilityName"+count);
            abilityNameLine.addField(new ReportField<>(
                    "ABILITY",
                    new StaticField<>(slot.getAbility().getName()),
                    0
            ));
            section1.addLine(abilityNameLine);
            ReportLine<PokemonApiResponse> abilityUrlLine = new ReportLine<>("abilityUrl"+count);
            abilityUrlLine.addField(new ReportField<>(
                    null,
                    new StaticField<>(slot.getAbility().getUrl()),
                    0
            ));
            section1.addLine(abilityUrlLine);
            count+=1;
        }


        section1.getLine("abilityName1").addField(new ReportField<>(
                "Name",
                new MappedField<>(PokemonApiResponse::getName),
                section1Separator
        ));
        section1.getLine("abilityUrl1").addField(new ReportField<>(
                "E",
                new MappedField<>(PokemonApiResponse::getId),
                section1Separator
        ));

        section1.addLine(line1);
        section1.addLine(new ReportLine<>("empty"));
        section1.addLine(line2);

        ReportLine<PokemonApiResponse> line5 = new ReportLine<>("phones");
        line5.addField(new ReportField<>("PH", new MappedField<>(p -> p.getTypes().get(0).getType().getName()), 0));
        line5.addField(new ReportField<>("PH", new MappedField<>(p -> p.getAbilities().get(0).getAbility().getName()), 30));

        ReportLine<PokemonApiResponse> line6 = new ReportLine<>("subPrimary");
        line6.addField(new ReportField<>("SUB PRIMARY:", new MappedField<>(p -> p.getTypes().get(0).getType().getUrl()), 0));

        ReportSection<PokemonApiResponse> section2 = new ReportSection<>("DEMOGRAPHICS");
        section2.addLine(line5);
        section2.addLine(line6);

        ReportBuilder<PokemonApiResponse> builder = new ReportBuilder<>();
        builder.addSection(section1);
        builder.addSection(section2);


        return builder.build(pokemonApiResponse);
    }

    public static List<Report> toEntities(Long pokemonApiId, List<String> lines) {
        return IntStream.range(0, lines.size())
                .mapToObj(i -> new Report()
                        .setPokemonApiId(pokemonApiId)
                        .setSequenceNumber(i + 1)
                        .setDataLine(lines.get(i)))
                .collect(Collectors.toList());
    }
}
