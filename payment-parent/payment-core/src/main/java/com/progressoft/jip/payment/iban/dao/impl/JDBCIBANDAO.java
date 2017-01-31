package com.progressoft.jip.payment.iban.dao.impl;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class JDBCIBANDAO implements IBANDAO {

    private static final String SELECT_IBAN_STATMRNT_BASED_ON_IBANID = "select * from iban WHERE iban_id=";
    private static final String SELECT_ALL_IBAN_STATMENT = "select * from iban ; ";
    private static final String INSERT_IBAN_STATMENT = "insert into iban values(?,?,?)";
    private static final String TABLE_NAME = "iban";
    private final QueryRunner queryRunner;
    private final IdDAO idDAO;

    public JDBCIBANDAO(DataSource dataSource) {
        this.queryRunner = new QueryRunner(dataSource);
        this.idDAO = new IdDAOImpl(dataSource);

    }

    public IBANDTO save(IBANDTO ibandto) {
        int id = idDAO.save(TABLE_NAME);
        try {
            int updated = this.queryRunner.update(INSERT_IBAN_STATMENT, id, ibandto.getCountryCode(),
                    ibandto.getIBANValue());
            if (updated == 0) {
                throw new DAOException("cannot Save Iban info: " + ibandto.getIBANValue());
            } else {
                return populateIBANDTO(ibandto, id);
            }

        } catch (SQLException e) {
            throw new DAOException("Error While Saving Iban: " + ibandto.getIBANValue(), e);
        }
    }

    public IBANDTO get(String iban) {
        try {
            Map<String, Object> query = this.queryRunner.query("select * from iban WHERE iban_value = '" + iban + "'", new MapHandler());
            IBANDTO populateIBANDTO = populateIBANDTO(query);
            if (Objects.isNull(populateIBANDTO))
                throw new DAOException("cannot get iban  : " + iban);
            return populateIBANDTO;
        } catch (SQLException e) {
            throw new DAOException("Error While Fetching IBAN : " + iban, e);
        }

    }

    private IBANDTO populateIBANDTO(Map<String, Object> query) {
        IBANDTOImpl ibanDTO = new IBANDTOImpl();
        ibanDTO.setCountryCode((String) query.get("country_code"));
        ibanDTO.setId(Integer.parseInt(query.get("iban_id").toString()));

        ibanDTO.setIbanValue((String) query.get("iban_value"));

        return ibanDTO;
    }

    public IBANDTO getById(int id) {
        try {
            Map<String, Object> query = this.queryRunner.query(SELECT_IBAN_STATMRNT_BASED_ON_IBANID + id, new MapHandler());
            IBANDTO populateIBANDTO = populateIBANDTO(query);
            if (Objects.isNull(populateIBANDTO))
                throw new DAOException("cannot get iban by id : " + id);
            return populateIBANDTO;
        } catch (SQLException e) {
            throw new DAOException("Error While Fetching IBAN ID : " + id, e);
        }
    }

    public Iterable<IBANDTO> getAll() {
        try {
            final List<Map<String, Object>> ibans = this.queryRunner.query(SELECT_ALL_IBAN_STATMENT,
                    new MapListHandler());

            if (ibans.isEmpty())
                throw new DAOException("There's No Ibans Table Is Empty ");

            final List<IBANDTO> ibansDTO = new ArrayList<>();
            ibansDTO.addAll(fillIbanDTO(ibans));
            return new Iterable() {

                @Override
                public Iterator<IBANDTO> iterator() {
                    return ibansDTO.iterator();
                }
            };
        } catch (SQLException e) {
            throw new DAOException("Error While Fetching All IBAN : ", e);
        }
    }

    private List<IBANDTO> fillIbanDTO(List<Map<String, Object>> ibans) {
        List<IBANDTO> ibanList = new ArrayList<>();
        ibans.stream().forEach(pair -> {
            IBANDTOImpl ibandto = new IBANDTOImpl();
            ibandto.setIbanValue((String) pair.get("iban_value"));
            ibandto.setCountryCode((String) pair.get("country_code"));
            ibandto.setId((int) pair.get("iban_id"));
            ibanList.add(ibandto);
        });

        return ibanList;

    }

    private IBANDTOImpl populateIBANDTO(IBANDTO ibandto, int id) {
        IBANDTOImpl ibanDtoImpl = new IBANDTOImpl();
        ibanDtoImpl.setId(id);
        ibanDtoImpl.setCountryCode(ibandto.getCountryCode());
        ibanDtoImpl.setIbanValue(ibandto.getIBANValue());
        return ibanDtoImpl;
    }
}
