/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.util.Date;

/**
 *
 * @author tmaleka
 */
public class FlashFigures {
    
    private String storeNumber;
    private String storeDescription;
    private String period;
    private Date lastDayEndDate;
    
    private Double dailySales;
    private Double monthlySales;
    private Double salesBudget;
    private Double salesPercentageAgainstBudet;
    
    
}

/*

 daily_sales                    | numeric(16,2)               |
 sales_mtd                      | numeric(16,2)               |
 sales_budget                   | numeric(16,2)               |
 sales_perc_of_budget           | numeric(16,2)               |
 mtd_sales_last_year            | numeric(16,2)               |
 sales_perc_of_last_year        | numeric(16,2)               |
 cost_of_sales                  | numeric(16,2)               |
 mtd_margin_perc                | numeric(16,2)               |
 mtd_cash_sales                 | numeric(16,2)               |
 cash_sales_perc_of_total       | numeric(16,2)               |
 mtd_deposits                   | numeric(16,2)               |
 deposit_perc_of_crd_sales      | numeric(16,2)               |
 other_income_excl_vat          | numeric(16,2)               |
 other_income_perc_of_crd_sales | numeric(16,2)               |
 open_acc_instalments           | numeric(16,2)               |
 daily_instalments              | numeric(16,2)               |
 mtd_instalments                | numeric(16,2)               |
 instalments_budget             | numeric(16,2)               |
 instalments_perc_of_budget     | numeric(16,2)               |
 instalments_last_year          | numeric(16,2)               |
 debtors_ledger                 | numeric(16,2)               |
 mtd_cols_perc_of_ledger        | numeric(16,2)               |
 mtd_cols_last_year             | numeric(16,2)               |
 stock_at_cost                  | numeric(16,2)               |
 credit_notes                   | numeric(16,2)               |
 handling                       | numeric(16,2)               |
 disc                           | numeric(16,2)               |
 mark_down                      | numeric(16,2)               |
 repo_rfc                       | numeric(16,2)               |


*/