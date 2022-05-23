package com.wordled.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.wordled.security.SecurityService;
import com.wordled.security.SecurityUtils;
import com.wordled.views.components.CustomMenuBar;

import java.util.ArrayList;
import java.util.List;

@Route("leaderboard")
public class LeaderboardView extends VerticalLayout {


    private Grid<Player> grid;

    private List<Player> players;

    public LeaderboardView(SecurityService securityService) {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout gridContainer = new HorizontalLayout();
        gridContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        gridContainer.setAlignItems(Alignment.CENTER);

        players = new ArrayList<>();
        setAlignItems(Alignment.CENTER);
        playerGenerator(17);
        generateGrid();

        gridContainer.add(grid);

        verticalLayout.add(new CustomMenuBar(securityService, SecurityUtils.isUserLoggedIn()).getMenuBar(), new H1("!Wordled"), gridContainer);

        add(verticalLayout);

    }

    private void generateGrid() {
        this.grid = new Grid<>();
        grid.setWidth(400f, Unit.PIXELS);
        grid.addColumn(Player::getRank).setHeader("Rank");
        grid.addColumn(Player::getUsername).setHeader("Username");
        grid.addColumn(Player::getPoints).setHeader("Points").setTextAlign(ColumnTextAlign.END);
        grid.setItems(players);
    }

    /*
    EXAMPLE LIST USAGE
     */
    private void playerGenerator(int amount) {
        for(int i = 0; i < amount; i++) {
            players.add(new Player(i + 1, "Player" + i, (i + 100) / (i + 1)));
        }
    }

    private static class Player {

        private int rank;
        private String username;
        private int points;

        public Player(int rank, String username, int points) {

            this.rank = rank;
            this.username = username;
            this.points = points;

        }

        public int getRank() {
            return rank;
        }

        public String getUsername() {
            return username;
        }

        public int getPoints() {
            return points;
        }

    }


}
