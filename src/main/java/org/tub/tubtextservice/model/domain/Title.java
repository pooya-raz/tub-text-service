package org.tub.tubtextservice.model.domain;

import org.tub.tubtextservice.model.domain.person.Person;

import java.util.Objects;

public record Title(
        String titleTransliterated,
        String titleArabic,
        Person person,
        String description,
        Category category,
        TitleType titleType,
        String baseText
        ) {
    public Title {
        if(Objects.isNull(titleType)){
            titleType = TitleType.Unknown;
        }
        if(Objects.isNull(category)){
            category = Category.Unknown;
        }
    }
    public static Builder builder(){
       return new Builder();
    }

    public static final class Builder{
        String titleTransliterated;
        String titleArabic;
        Person person;
        String description;
        Category category;
        TitleType titleType;
        String baseText;

        public Builder titleTransliterated(String titleTransliterated){
            this.titleTransliterated = titleTransliterated;
            return this;
        }
        public Builder titleArabic(String titleArabic){
            this.titleArabic = titleArabic;
            return this;
        }
        public Builder person(Person person){
            this.person = person;
            return this;
        }
        public Builder description(String description){
            this.description = description;
            return this;
        }
        public Builder category(Category category){
            this.category = category;
            return this;
        }
        public Builder titleType(TitleType titleType){
            this.titleType = titleType;
            return this;
        }
        public Builder baseText(String baseText){
            this.baseText = baseText;
            return this;
        }
        public Title build(){
            return new Title(titleTransliterated, titleArabic, person, description, category, titleType, baseText);
        }
    }
}
