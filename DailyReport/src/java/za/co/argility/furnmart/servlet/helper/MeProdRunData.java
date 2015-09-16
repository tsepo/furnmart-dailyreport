/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.servlet.helper;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luthando
 */
public class MeProdRunData {
    
    private List<String> fpp_cde;

    public List<String> getFpp_cde() {
        return fpp_cde;
    }

    public List<String> getBr_cde() {
        return br_cde;
    }

    public List<Date> getMendstat_start() {
        return mendstat_start;
    }

    public List<Date> getMendstat_end() {
        return mendstat_end;
    }

    public List<Integer> getMendstat_error_id() {
        return mendstat_error_id;
    }
    private List<String> br_cde;

    public void setFpp_cde(List<String> fpp_cde) {
        this.fpp_cde = fpp_cde;
    }

    public void setBr_cde(List<String> br_cde) {
        this.br_cde = br_cde;
    }

    public void setMendstat_start(List<Date> mendstat_start) {
        this.mendstat_start = mendstat_start;
    }

    public void setMendstat_end(List<Date> mendstat_end) {
        this.mendstat_end = mendstat_end;
    }

    public void setMendstat_error_id(List<Integer> mendstat_error_id) {
        this.mendstat_error_id = mendstat_error_id;
    }
    private List<Date> mendstat_start;
    private List<Date> mendstat_end;
    private List<Integer> mendstat_error_id;
    
    private List<String> debtors;

    public void setDebtors(List<String> debtors) {
        this.debtors = debtors;
    }

    public void setCreditors(List<String> creditors) {
        this.creditors = creditors;
    }

    public void setCashBook(List<String> cashBook) {
        this.cashBook = cashBook;
    }

    public void setNewGl(List<String> newGl) {
        this.newGl = newGl;
    }

    public void setBucketsReport(List<String> bucketsReport) {
        this.bucketsReport = bucketsReport;
    }
    private List<String> creditors;

    public List<String> getDebtors() {
        return debtors;
    }

    public List<String> getCreditors() {
        return creditors;
    }

    public List<String> getCashBook() {
        return cashBook;
    }

    public List<String> getNewGl() {
        return newGl;
    }

    public List<String> getBucketsReport() {
        return bucketsReport;
    }
    private List<String>  cashBook;
    private List<String>  newGl;
    private List<String>  bucketsReport;
}
