package com.ozgur.laboratoryreportingapplication.dev_temp;

public class DummyReportsDatabase {

    String[][] patientNames = {{"John", "Robert", "Marry", "Alan", "Warren", "Sophia", "Emma", "Darian"},
            {"Ahmet", "Mehmet", "Ayşe", "Fatma", "Ali", "Mustafa", "Cem", "Ece"}};
    String[][] patientSurnames = {{"Avery", "Abel", "Erik", "Jacob", "Pedro", "Tristan", "Luis", "Cole"},
            {"Yılmaz", "Demir", "Kaya", "Çelik", "Şahin", "Koç", "Güneş", "Yıldız"}};

    String[] diagnosisTitles = {
            "Complete Blood Count (CBC)",
            "Lipid Profile",
            "Liver Function Test (LFT)",
            "Renal Function Test (RFT)",
            "Thyroid Function Test (TFT)",
            "Blood Glucose Test",
            "Urine Analysis",
            "Electrolyte Panel",
            "Hemoglobin A1c Test",
            "Chest X-ray"
    };

    String[] diagnosisDetails = {
            "Blood test showing levels of red and white blood cells, hemoglobin, and platelets.",
            "Measurement of cholesterol, triglycerides, and other lipid levels in the blood.",
            "Assessment of liver enzymes, bilirubin, and other liver-related parameters.",
            "Examination of kidney function by measuring creatinine, urea, and electrolyte levels.",
            "Evaluation of thyroid hormones TSH, T3, and T4 to assess thyroid health.",
            "Measurement of fasting blood sugar levels to diagnose diabetes or prediabetes.",
            "Analysis of urine sample to detect abnormalities and infections.",
            "Measurement of electrolytes like sodium, potassium, and chloride in the blood.",
            "Test indicating average blood sugar levels over the past 2-3 months.",
            "Imaging technique to visualize the chest and diagnose lung conditions."
    };


    public String getPatientName(int index1, int index2) {
        if (index1 >= 0 && index2 >= 0 && index1 < patientNames.length && index2 < patientNames[index1].length) {
            return patientNames[index1][index2];
        }
        return "Invalid Index";
    }

    public String getPatientSurname(int index1, int index2) {
        if (index1 >= 0 && index2 >= 0 && index1 < patientSurnames.length && index2 < patientSurnames[index1].length) {
            return patientSurnames[index1][index2];
        }
        return "Invalid Index";
    }

    public String getDiagnosisTitle(int index) {
        if (index >= 0 && index < diagnosisTitles.length) {
            return diagnosisTitles[index];
        }
        return "Invalid Index";
    }

    public String getDiagnosisDetails(int index) {
        if (index >= 0 && index < diagnosisDetails.length) {
            return diagnosisDetails[index];
        }
        return "Invalid Index";
    }
}
