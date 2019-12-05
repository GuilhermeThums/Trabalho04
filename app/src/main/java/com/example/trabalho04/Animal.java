package com.example.trabalho04;

import java.util.Date;

public class Animal {
    private int id;
    private String animalNome;
//    private String animalEspecie;
//    private String animalSexo;
//    private String animalCor;
//    private Double animalPeso;
//    private String vacina1;
//    private String vacina2;
//    private String vacina3;
//    private String vacina4;
//    private Date animalNascimento;

    public Animal(int id, String animalNome) {
        setId(id);
        this.animalNome = animalNome;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnimalNome() {
        return animalNome;
    }

    public void setAnimalNome(String animalNome) {
        this.animalNome = animalNome;
    }

//    public String getAnimalEspecie() {
//        return animalEspecie;
//    }
//
//    public void setAnimalEspecie(String animalEspecie) {
//        this.animalEspecie = animalEspecie;
//    }
//
//    public String getAnimalSexo() {
//        return animalSexo;
//    }
//
//    public void setAnimalSexo(String animalSexo) {
//        this.animalSexo = animalSexo;
//    }
//
//    public String getAnimalCor() {
//        return animalCor;
//    }
//
//    public void setAnimalCor(String animalCor) {
//        this.animalCor = animalCor;
//    }
//
//    public Double getAnimalPeso() {
//        return animalPeso;
//    }
//
//    public void setAnimalPeso(Double animalPeso) {
//        this.animalPeso = animalPeso;
//    }
//
//    public String getVacina1() {
//        return vacina1;
//    }
//
//    public void setVacina1(String vacina1) {
//        this.vacina1 = vacina1;
//    }
//
//    public String getVacina2() {
//        return vacina2;
//    }
//
//    public void setVacina2(String vacina2) {
//        this.vacina2 = vacina2;
//    }
//
//    public String getVacina3() {
//        return vacina3;
//    }
//
//    public void setVacina3(String vacina3) {
//        this.vacina3 = vacina3;
//    }
//
//    public String getVacina4() {
//        return vacina4;
//    }
//
//    public void setVacina4(String vacina4) {
//        this.vacina4 = vacina4;
//    }
//
//    public Date getAnimalNascimento() {
//        return animalNascimento;
//    }
//
//    public void setAnimalNascimento(Date animalNascimento) {
//        this.animalNascimento = animalNascimento;
//    }


    @Override
    public String toString()
    {
        return getAnimalNome();
    }
}
