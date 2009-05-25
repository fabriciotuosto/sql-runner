package org.sql.runner.scripts.java;

import java.io.IOException;
import java.io.Writer;

import org.excel4j.ExcelRow;

public class ProductoComercialFunction implements ScriptFunction
{
	private static final String SHEET_NAME = "Producto Comercial";
	
	public String getSheetName() {
		return SHEET_NAME;
	}

	public void processRow(ExcelRow row, Writer out) {
		if (!"si".equalsIgnoreCase(row.getColumn("S"))){return;}
		try {
			String subquery_producto_tecnico = String.format("SELECT p_id FROM t_producto_tecnico_papel WHERE A_DESCRIPCION = '%s'",row.getColumn('I'));
			String subquery_tipo_operacion = String.format("SELECT p_id FROM T_TIPO_OPERACION_COMERCIAL WHERE upper(A_DESCRIPCION) = upper('%s')",row.getColumn('K'));
			String subquery_nivel_est = String.format("SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('%s') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('%s') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('%s') )", row.getColumn('C'),row.getColumn('B'),row.getColumn('A'));
			String id_geometria= String.format("SELECT P_ID FROM T_GEOMETRIA_PAPEL WHERE A_NOMBRE = '%s'",row.getColumn('S'));
			String subquery_prod_com = String.format("SELECT P_ID FROM T_CONFIG_PROD_COM_PAPEL_CLASIF WHERE f_producto_comercial IN (SELECT p_id FROM t_producto_com_papel_clas WHERE p_id in (SELECT p_id FROM T_PROD_COMERCIAL WHERE upper(a_nombre) = upper('%s')) AND f_producto_tecnico = (%s) AND f_tipo_operacion = (%s) AND f_nivel_estr_papel_clasif = (%s)",row.getColumn('G'),subquery_tipo_operacion,subquery_nivel_est,subquery_producto_tecnico);
			String bool_anuncioempadronado = (true) ? "Y" : "N";
			out.write(String.format("INSERT INTO T_PROD_COMERCIAL (P_ID,A_DESCRIPCION,A_NOMBRE) VALUES (S_PROD_COMERCIAL.NEXTVAL,'%s','%s');%n", row.getColumn('H'),row.getColumn('G')));
			out.write(String.format("INSERT INTO T_PROD_COM_PAPEL_CLAS_UBIC (P_ID, F_NIVEL_EST_PAPEL_CLASIFICADOS, F_PRODUCTO_PAPEL_CLASIFICADOS) VALUES ( S_PROD_COM_PAPEL_CLAS_UBIC.NEXTVAL, (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('%s') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('%s') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('%s') ) ) ), ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('%s') ) );%n",row.getColumn('C'),row.getColumn('B'),row.getColumn('A'),row.getColumn('A'))); 
			out.write(String.format("INSERT INTO T_PRODUCTO_COM_PAPEL_CLAS (P_ID,A_ANUNCIANTE_EMPADRONADO,F_TIPO_OPERACION, F_PRODUCTO_TECNICO, F_NIVEL_ESTR_PAPEL_CLASIF, F_PROD_COM_PAPEL_CLAS_UBIC) VALUES (S_PROD_COMERCIAL.CURRVAL, '%s', (%s), (%s), (%s), S_PROD_COM_PAPEL_CLAS_UBIC.CURRVAL );%n",bool_anuncioempadronado,subquery_tipo_operacion,subquery_producto_tecnico,subquery_nivel_est));
			out.write(String.format("INSERT INTO T_RANGO_FECHA (P_ID,A_DESCRIPCION,A_FECHA_DESDE,A_FECHA_HASTA) VALUES (S_RANGO_FECHA.NEXTVAL, 'Producto Comercial',TO_DATE('31-12-1899', 'DD-MM-YYYY'),TO_DATE('31-12-9999', 'DD-MM-YYYY'));%n"));
			out.write(String.format("INSERT INTO T_CONFIG_PROD_COM_PAPEL_CLASIF (P_ID,F_GEOMETRIA,F_PRODUCTO_COMERCIAL) VALUES (S_RANGO_FECHA.CURRVAL,( %s ),S_PROD_COMERCIAL.CURRVAL);%n",id_geometria));
			out.write(String.format("INSERT INTO T_CONF_PROD_COM_CANAL_ENTRADA (F_CONFIG_PROD_COMERCIAL,F_CANAL_ENTRADA) VALUES (S_RANGO_FECHA.CURRVAL, 1);%n"));
			
			for (String token : row.getColumn("R").split(";"))
			{
				out.write(String.format("INSERT INTO T_CONF_PROD_COM_TEMPLATE_DETAL (F_CONFIG_PROD_COMERCIAL, F_TEMPLATE_DETALLADO) VALUES ( (%s) , (SELECT P_ID FROM T_TEMPLATE_DETALLADO WHERE A_NOMBRE = '%s') );%n",subquery_prod_com,token));
			}
		} catch (IOException e) {
			throw new Error(e);
		}
	}

}
