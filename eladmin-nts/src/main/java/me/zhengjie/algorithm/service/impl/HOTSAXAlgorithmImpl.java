package me.zhengjie.algorithm.service.impl;

import lombok.RequiredArgsConstructor;
import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.discord.DiscordRecords;
import net.seninp.jmotif.sax.discord.HOTSAXImplementation;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class HOTSAXAlgorithmImpl extends AlgorithmImpl {

    @Override
    public DiscordRecords series2AnomaliesRecords(double[] series, int windowSize, Map<String, String> params) {
        DiscordRecords discordRecords = null;
        try {
            discordRecords = HOTSAXImplementation.series2Discords(series, Integer.parseInt(params.get(DISCORDS_TO_TEST)), windowSize,
                    Integer.parseInt(params.get(PAA_SIZE)), Integer.parseInt(params.get(ALPHABET_SIZE)), NumerosityReductionStrategy.NONE, Double.parseDouble(params.get(NORM_THRESHOLD)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discordRecords;
    }
}
