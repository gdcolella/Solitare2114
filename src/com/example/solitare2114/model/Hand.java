package com.example.solitare2114.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

// -------------------------------------------------------------------------
/**
 * this class is for hands, which are represented in stacks.
 *
 * @author Gregory Colella (gregc@vt.edu), Andrew Bryant (andrewpb), & Pelin
 *         Demir (pelind@vt.edu)
 * @version Nov 17, 2014
 */
public class Hand
    implements Iterable<Card>
{
    /**
     * this field is for the Stack cardsInHand which is to be made up of Cards
     */
    Stack<Card> cardsInHand;
    /**
     * this field is for the value of the rule ableToAdd, which tells us if we
     * can add one card ontop of another in a given situation.
     */
    Rule        ableToAdd;


    // ----------------------------------------------------------
    /**
     * Create a new Hand object
     *
     * @param canAdd
     */
    public Hand(Rule canAdd)
    {
        this.ableToAdd = canAdd;
        cardsInHand = new Stack<Card>();
    }


    // ----------------------------------------------------------
    /**
     * peek method to use for seeing the last card added in a deck, but not
     * removing it like pop would.
     *
     * @return the value of the top card in cardsInHand
     */
    public Card peek()
    {
        return cardsInHand.peek();
    }


    // ----------------------------------------------------------
    /**
     * pop method to use for removing the card last added to cardsInHand
     *
     * @return the value of the last added card.
     */
    public Card pop()
    {
        return cardsInHand.pop();
    }


    // ----------------------------------------------------------
    /**
     * adds the value of the card parameter to the stack cardsInHand
     *
     * @param canAdd
     *            is the card to add to the stack
     */
    public void add(Card canAdd)
    {
        if (canAdd(new Cards(canAdd)))
        {
            canAdd.currentlyIn = this;
            cardsInHand.add(canAdd);
        }
        else
        {
            throw new IllegalStateException("Tried to add a " + canAdd
                + " to an" + " illegal hand.");
        }
    }


    // ----------------------------------------------------------
    /**
     * checks if cardsInHand is empty or not
     *
     * @return boolean value of if the stack is or is not empty.
     */
    public boolean isEmpty()
    {
        return cardsInHand.isEmpty();
    }


    // ----------------------------------------------------------
    /**
     * force add is for dealing the cards, so that you do not have to follow the
     * rules of the game when dealing.
     *
     * @param canAdd
     *            is the card being dealt.
     */
    public void forceAdd(Card canAdd)
    {
        canAdd.currentlyIn = this;
        cardsInHand.add(canAdd);
    }


    // ----------------------------------------------------------
    /**
     * forceAddAll of Hand for dealing purposes when order of cards are going to
     * break the rules of moving them.
     *
     * @param cards
     */
    public void forceAddAll(Collection<Card> cards)
    {
        for (Card c : cards)
            forceAdd(c);
    }


    // ----------------------------------------------------------
    /**
     * returns the cards in a hand starting from the param card
     *
     * @param startWith
     *            is the card that is the first card in the list of cards that
     *            are being moved
     * @return the list of cards starting with the param card
     */
    public Cards cardsStartingFrom(Card startWith)
    {
        int index = cardsInHand.size() - cardsInHand.indexOf(startWith);

        return cardsStartingFrom(index);

    }


    public Cards cardsStartingFrom(int index) {
        return new Cards(cardsInHand.subList(cardsInHand.size() - index, cardsInHand.size()));
    }

    // ----------------------------------------------------------
    /**
     * Moves the list of cards whichCards into the hand moveTo
     * @param moveTo is the hand that the cards are being moved to
     * @param whichCards is the cards being moved
     * @return the boolean of if it is possible
     */
    public boolean moveCardsTo(Hand moveTo, Cards whichCards)
    {

        if (!moveTo.canAdd(whichCards))
        {
            return false;
        }
        forceMoveCardsTo(moveTo, whichCards, true );
        return true;

    }

    public void forceMoveCardsTo(Hand moveTo,
        Cards whichCards, boolean flipMine) {

        for (Card c : whichCards)
        {
            cardsInHand.remove(c);
            moveTo.forceAdd(c);
            c.currentlyIn = moveTo;
        }

        if(!isEmpty() && flipMine) {
            if (!peek().facedUp)
                peek().flipOver();
        }


    }


    // ----------------------------------------------------------
    /**
     * @param toAdd
     *            is card value that is trying to be added to the hand.
     * @return the boolean value of if it is able to add or not.
     */
    public boolean canAdd(Cards toAdd)
    {
        return ableToAdd.canAdd(this, toAdd);
    }


    /**
     * iterator for the cards in a hand.
     */
    public Iterator<Card> iterator()
    {
        return cardsInHand.iterator();
    }


    // ----------------------------------------------------------
    /**
     * getter for the size of Hand
     * @return the size
     */
    public int size()
    {
        return cardsInHand.size();
    }
}
