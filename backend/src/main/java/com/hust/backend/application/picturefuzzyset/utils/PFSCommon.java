package com.hust.backend.application.picturefuzzyset.utils;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.entity.PatientSymptomEntity;
import com.hust.backend.application.picturefuzzyset.entity.SymptomDiagnoseEntity;
import com.hust.backend.application.picturefuzzyset.model.GeneralPictureFuzzySet;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.tuple.Tuple3;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.apache.commons.math3.util.Precision.EPSILON;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PFSCommon {

    public static Tuple3<DiagnoseEnum, PictureFuzzySet, Double> computeRelation(
            DiagnoseEnum diagnoseEnum,
            List<PatientSymptomEntity> PSRelations,
            List<SymptomDiagnoseEntity> SDRelations
    ) {
        double maxPositive = -2.0;
        double minNeutral = 2.0;
        double minNegative = 2.0;
        for (int i = 0; i < PSRelations.size(); ++i) {
            PatientSymptomEntity psRelation = PSRelations.get(i);
            SymptomDiagnoseEntity sdRelation = SDRelations.get(i);

            double psPositive = psRelation.getPictureFuzzySet().getPositive();
            double psNeutral = psRelation.getPictureFuzzySet().getNeutral();
            double psNegative = psRelation.getPictureFuzzySet().getNegative();

            double sdPositive = sdRelation.getPictureFuzzySet().getPositive();
            double sdNeutral = sdRelation.getPictureFuzzySet().getNeutral();
            double sdNegative = sdRelation.getPictureFuzzySet().getNegative();

            maxPositive = Math.max(Math.min(psPositive, sdPositive), maxPositive);
            minNeutral = Math.min(Math.min(psNeutral, sdNeutral), minNeutral);
            minNegative = Math.min(Math.max(psNegative, sdNegative), minNegative);
        }
        PictureFuzzySet pfs = PictureFuzzySet.builder()
                .positive(maxPositive)
                .neutral(minNeutral)
                .negative(minNegative)
                .build();
        double probability = calPDCorrespondence(pfs);
        return Tuple3.of(diagnoseEnum, pfs, Common.round(probability, 3));
    }

    public static boolean isValidPFS(PictureFuzzySet pfs) {
        return pfs.getPositive() + pfs.getNeutral() + pfs.getNegative() <= 1 + EPSILON;
    }

    // TODO: validate invalid linguistic input
    public static boolean isValidGPFS(GeneralPictureFuzzySet gpfs) {
        return (gpfs.getPositive() instanceof Number || gpfs.getPositive() instanceof String) &&
                (gpfs.getNeutral() instanceof Number || gpfs.getNeutral() instanceof String) &&
                (gpfs.getNegative() instanceof Number || gpfs.getNegative() instanceof String);
    }

    public static Double calPDCorrespondence(PictureFuzzySet pfs) {
        return pfs.getPositive() - pfs.getNeutral() *
                (1 - (pfs.getPositive() + pfs.getNeutral() + pfs.getNegative()));
    }

}
