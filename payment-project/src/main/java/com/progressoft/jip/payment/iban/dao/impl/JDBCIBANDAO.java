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
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.id.generator.IdDAO;
import com.progressoft.jip.payment.id.generator.IdDAOImpl;

public class JDBCIBANDAO implements IBANDAO {

	private final QueryRunner queryRunner;
	private final IdDAO idDAO;
	private static final String TABLE_NAME = "iban";

	public JDBCIBANDAO(DataSource dataSource) {
		this.queryRunner = new QueryRunner(dataSource);
		idDAO = new IdDAOImpl(dataSource);

	}

	public IBANDTO save(IBANDTO ibandto) {
		// long id = idDAO.insert(TABLE_NAME);
		long id = ibandto.getId();

		String insert_iban = "insert into iban values(?,?,?)";
		try {
			int updated = this.queryRunner.update(insert_iban, id, ibandto.getCountryCode(), ibandto.getIBANValue());
			if (updated == 0) {
				throw new DAOException("Error While Saving Iban: " + ibandto.getIBANValue());
			} else {
				IBANDTOImpl ibanDtoImpl = populateIBANDTO(ibandto, id);
				return ibanDtoImpl;
			}

		} catch (SQLException e) {
			throw new DAOException("Error While Saving Iban: " + ibandto.getIBANValue());
		}
	}

	public IBANDTO get(String iban) {
		String get_iban = "select * from iban WHERE iban_value=?";

		try {
			IBANDTOImpl ibanDTO = this.queryRunner.query(get_iban, new BeanHandler<IBANDTOImpl>(IBANDTOImpl.class));
			if (ibanDTO == null)
				throw new DAOException("Error While Fetching IBAN : " + iban);
			return ibanDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching IBAN : " + iban);
		}

	}

	public IBANDTO getById(String id) {
		String get_iban = "select * from iban WHERE iban_id=?";

		try {
			IBANDTOImpl ibanDTO = this.queryRunner.query(get_iban, new BeanHandler<IBANDTOImpl>(IBANDTOImpl.class));
			if (ibanDTO == null)
				throw new DAOException("Error While Fetching IBAN ID : " + id);
			return ibanDTO;
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching IBAN ID : " + id);
		}
	}

	public Iterable<IBANDTO> getAll() {
		String get_iban = "select * from iban";

		try {
			  List<IBANDTOImpl> ibans = this.queryRunner.query(get_iban, new BeanListHandler<IBANDTOImpl>(IBANDTOImpl.class));
			 
			 
			if (ibans== null)
				throw new DAOException("Error While Fetching All IBAN : ");
			if(ibans.size()==0)
				throw new DAOException("There's No Iban Empty Table");
			
			final List<IBANDTO>ibansDTO=new ArrayList<IBANDTO>();
			ibansDTO.addAll(ibans);
			return new Iterable<IBANDTO>() {
				
				public Iterator<IBANDTO> iterator() {
					return ibansDTO.iterator();
				}
			};
		} catch (SQLException e) {
			throw new DAOException("Error While Fetching All IBAN : " );
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
