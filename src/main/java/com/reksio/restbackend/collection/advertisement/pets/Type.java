package com.reksio.restbackend.collection.advertisement.pets;

import com.reksio.restbackend.collection.advertisement.Category;

public enum Type {
    //DOGS
    Labrador_Retrievers(Category.DOGS),
    German_Shepherds(Category.DOGS),
    Golde_Retrievers(Category.DOGS),
    French_Bulldogs(Category.DOGS),
    Bulldogs(Category.DOGS),
    Beagles(Category.DOGS),
    Poodles(Category.DOGS),
    Rottweilers(Category.DOGS),
    German_Shorthaired_Pointers(Category.DOGS),
    Yorkshire_Terriers(Category.DOGS),

    //CATS
    Siamese(Category.CATS),
    Persian(Category.CATS),
    Maine_Coon(Category.CATS),
    Ragdoll(Category.CATS),
    Bengal(Category.CATS),
    Abyssinian(Category.CATS),
    Birman(Category.CATS),
    Oriental_Shorthair(Category.CATS),
    Sphynx(Category.CATS),
    Devon_Rex(Category.CATS),
    Himalayan(Category.CATS),
    American_Shorthair(Category.CATS),

    //BIRDS
    Canaries(Category.BIRDS),
    Budgies(Category.BIRDS),
    Finches(Category.BIRDS),
    Cockatiels(Category.BIRDS),
    Quaker_Parakeets(Category.BIRDS),
    Pionus_Parrots(Category.BIRDS),
    Poicephalus_Parrots(Category.BIRDS),
    Amazon_Parrots(Category.BIRDS),
    Pyrrhura_Conures(Category.BIRDS),
    Peach_Faced_Lovebirds(Category.BIRDS),

    //EXOTIC
    Kinkajou(Category.EXOTIC),
    Python(Category.EXOTIC),
    Monkey(Category.EXOTIC),
    Chimpanzee(Category.EXOTIC),

    //OTHERS
    Lizard(Category.OTHERS),
    Insects(Category.OTHERS),
    Arachnids(Category.OTHERS),
    Reptiles(Category.OTHERS),
    Rodents(Category.OTHERS),
    Hedgehogs(Category.OTHERS);

    private Category category;

    Type(Category category) {
        this.category = category;
    }

    public boolean isInCategory(Category category) {
        return this.category == category;
    }
}
