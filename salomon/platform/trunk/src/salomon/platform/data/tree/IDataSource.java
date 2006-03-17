/*
 * Copyright (C) 2005 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.platform.data.tree;

import java.util.Date;

import salomon.engine.solution.ShortSolutionInfo;

/**
 * Interfejs reprezentujacy Ÿród³o danych s³u¿¹ce do stworzenia drzewa 
 * decyzyjnego 
 * 
 * @author Mateusz Nowakowski
 *
 */
public interface IDataSource
{

    /**
     *  Identyfikator
     * @return identyfikator Ÿród³¹ danych
     */
    int getId();

    /**
     * Nazwa
     * @return nazwê
     */
    String getName();

    /**
     * Opis
     * @return opis
     */
    String getInfo();

    /**
     * Identyfikator rozwiazania okreœlajacy zewnetrzna baze danych 
     * 
     * @return identyfikator rozwiazania
     */
    ShortSolutionInfo getSolution();

    /**
     * Nazwa tabeli
     * 
     * @return nazwa tabeli
     */
    String getTableName();

    /**
     * Kolumna podlegajaca wnioskowaniu
     * 
     * @return kolumnê wnioskuj¹c¹
     */
    String getDecisionedColumn();

    /**
     * Kolumny s³u¿¹ce wnioskowaniu
     * 
     * @return kolumny wnioskuj¹ce
     */
    String[] getDecioningColumns();

    /**
     * Numer pierwszego wiersza w tabeli
     * 
     * @return numer pierwszego wiersza
     */
    int getFirstRowIndex();

    /**
     * Numer ostatniego wiersza w tabeli 
     * 
     * @return numer ostatniego wiersza w tabeli
     */
    int getLastRowIndex();

    /**
     * Audit CD
     * 
     * @return audit CD
     */
    Date getCreateDate();

    /**
     * Ustawia nazwê Ÿród³a danych
     * @param name nazwa Ÿród³a danych
     */
    void setName(String name);

    /**
     * Ustawia opis
     * @param info opis
     */
    void setInfo(String info);

    /**
     * Ustawia nazwê tabeli
     * @param tableName nazwa tabeli
     */
    void setTableName(String tableName);

    /**
     * Ustawia nazwê kolumny decyzyjnej
     * @param decisionedColum knolumna decyzyjna 
     */
    void setDecisionedColumn(String decisionedColumn);

    /**
     * Ustawia kolumny decyduj¹ce
     * @param decisioningColumns kolumny decyduj¹ce
     */
    void setDecioningColumns(String[] decisioningColumns);

    /**
     *  Ustawia numer pierwszego wiersza 
     * @param index numer pierwszego wiersza 
     */
    void setFirstRowIndex(int index);

    /**
     *  Ustawia numer ostatniego wiersza 
     * @param index numer ostatniego wiersza 
     */
    void setLastRowIndex(int index);

}
