package com.progressoft.jip.payment.iban.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

public class JDBCIBANDAO implements IBANDAO {

	private static final String SELECT_ALL_IBAN_STATMENT = "select * from iban";
	private static final String SELECT_IBAN_STATMENT = "select * from iban WHERE iban_id=?";
	private static final String INSERT_IBAN_STATMENT = "insert into iban values(?,?,?)";
	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String TABLE_NAME = "iban";

	public JDBCIBANDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		this.idDAO = new IdDAOImpl(dataSource);

	}

	public IBANDTO save(IBANDTO ibandto) {
		System.out.println("saveiban: " + (ibandto == null));
		long id = idDAO.save(TABLE_NAME);
		try {
			int updated = this.queryRunner.update(INSERT_IBAN_STATMENT, id, ibandto.getCountryCode(),
					ibandto.getIBANValue());
			if (updated == 0) {
				throw new DAOException("cannot Save Iban info: " + ibandto.getIBANValue());
			} else {
				IBANDTOImpl ibanDtoImpl = populateIBANDTO(ibandto, id);
				return ibanDtoImpl;
			}

		} catch (SQLException e) {
			throw new DAOException("Error While Saving Iban: " + ibandto.getIBANValue(), e);
		}
	}

	public IBANDTO get(String iban) {
		try {
			Map<String, Object> query = this.queryRunner.query(SELECT_IBAN_STATMENT, new MapHandler());
			IBANDTO populateIBANDTO = populateIBANDTO(query);
			if (populateIBANDTO == null)
				throw new DAOException("cannot get iban  : " + iban);
			return populateIBANDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching IBAN : " + iban, e);
		}

	}

	private IBANDTO populateIBANDTO(Map<String, Object> query) {
		IBANDTOImpl ibanDTO = new IBANDTOImpl();
		ibanDTO.setCountryCode((String) query.get("country_code"));
		ibanDTO.setId(Long.parseLong(query.get("iban_id").toString()));

		ibanDTO.setIbanValue((String) query.get("iban_value"));

		return ibanDTO;
	}

	public IBANDTO getById(long id) {
		try {
			final String SELECT_IBAN_STATMRNT_BASED_ON_IBANID = "select * from iban WHERE iban_id=" + id;

			Map<String, Object> query = this.queryRunner.query(SELECT_IBAN_STATMRNT_BASED_ON_IBANID, new MapHandler());
			IBANDTO populateIBANDTO = populateIBANDTO(query);
			if (populateIBANDTO == null)
				throw new DAOException("cannot get iban by id : " + id);
			return populateIBANDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching IBAN ID : " + id, e);
		}
	}

	public Iterable<IBANDTO> getAll() {
		try {
			List<IBANDTOImpl> ibans = this.queryRunner.query(SELECT_ALL_IBAN_STATMENT,
					new BeanListHandler<IBANDTOImpl>(IBANDTOImpl.class));

			if (ibans.size() == 0)
				throw new DAOException("There's No Iban Empty Table");

			final List<IBANDTO> ibansDTO = new ArrayList<IBANDTO>();
			ibansDTO.addAll(ibans);
			return new Iterable<IBANDTO>() {

				public Iterator<IBANDTO> iterator() {
					return ibansDTO.iterator();
				}
			};
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching All IBAN : ", e);
		}
	}

	private IBANDTOImpl populateIBANDTO(IBANDTO ibandto, long id) {
		IBANDTOImpl ibanDtoImpl = new IBANDTOImpl();
		ibanDtoImpl.setId(id);
		ibanDtoImpl.setCountryCode(ibandto.getCountryCode());
		ibanDtoImpl.setIbanValue(ibandto.getIBANValue());
		return ibanDtoImpl;
	}
}
