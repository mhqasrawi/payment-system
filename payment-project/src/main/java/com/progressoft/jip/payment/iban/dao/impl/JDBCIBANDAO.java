package com.progressoft.jip.payment.iban.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.payment.DAOException;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;

public class JDBCIBANDAO implements IBANDAO {

	private static final String SELECT_ALL_IBAN_STATMENT = "select * from iban";
	private static final String SELECT_IBAN_STATMRNT_BASED_ON_IBANID = "select * from iban WHERE iban_id=?";
	private static final String SELECT_IBAN_STATMENT = "select * from iban WHERE iban_value=?";
	private static final String INSERT_IBAN_STATMENT = "insert into iban values(?,?,?)";
	private final QueryRunner queryRunner;

	public JDBCIBANDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);

	}

	public IBANDTO save(IBANDTO ibandto) {
		long id = ibandto.getId();
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
			throw new DAOException("Error While Saving Iban: " + ibandto.getIBANValue());
		}
	}

	public IBANDTO get(String iban) {
		try {
			IBANDTOImpl ibanDTO = this.queryRunner.query(SELECT_IBAN_STATMENT,
					new BeanHandler<IBANDTOImpl>(IBANDTOImpl.class));
			if (ibanDTO == null)
				throw new DAOException("cannot get iban  : " + iban);
			return ibanDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching IBAN : " + iban);
		}

	}

	public IBANDTO getById(String id) {
		try {
			IBANDTOImpl ibanDTO = this.queryRunner.query(SELECT_IBAN_STATMRNT_BASED_ON_IBANID,
					new BeanHandler<IBANDTOImpl>(IBANDTOImpl.class));
			if (ibanDTO == null)
				throw new DAOException("cannot get iban by id : " + id);
			return ibanDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching IBAN ID : " + id);
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
			throw new DAOException("Error While Fetching All IBAN : ");
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
