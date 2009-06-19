--		INSERCION DE T_ORDENAMIENTO_AVISO		--
<#list repository.getSheet('T_ORDENAMIENTO_AVISO').getRows(2) as row>
	<#if row.getColumn('M') == 'Si'>
		<#assign niv_estr ="SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('L')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('K')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('J')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('I')}') ) ) ) " >
		<#if row.getColumn('L') == 'NULL'>
			<#assign niv_estr= "SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('K')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('J')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('I')}') ) " >
		</#if>
		<#assign orden_padre="NULL" >
		
		<#if row.getColumn('G') != 'NULL' >
			<#list repository.getSheet('T_ORDENAMIENTO_AVISO').getRows(2) as sub_row>
				<#assign sub_niv_estr="SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('L')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('K')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('J')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${sub_row.getColumn('I')}') ) ) )">
				<#if sub_row.getColumn('L') == 'NULL' >
					<#assign niv_estr= "SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('K')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('J')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${sub_row.getColumn('I')}') ) ">
				</#if>				 
			</#list>			
		</#if>
		INSERT INTO T_ORDENAMIENTO_AVISO ( P_ID, A_ORDEN, A_DESCRIPCION, A_TIPO_DATO, A_VALIDACION, A_OBLIGATORIO, F_ORDENAMIENTO_AVISO_PADRE, A_FORMATO, F_ID_NIVEL_EST_PAPEL ) VALUES ( S_ORDENAMIENTO_AVISO.NEXTVAL, '${row.getColumn('B')}', '${row.getColumn('C')}','${row.getColumn('D')}', '${row.getColumn('E')}', '${row.getColumn('F')}', (${orden_padre}), '${row.getColumn('H')}', (${niv_estr}));
	</#if>
</#list>