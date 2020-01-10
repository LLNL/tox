package gov.llnl.tox.util;
//---------------------------------------------------
import java.sql.*;
//----------------------------------------------------
import javax.naming.*;
import javax.sql.*;
//---------------------------------------------------
import oracle.jdbc.*;
//----------------------------------------------------
public class database
	{
	private Connection conn;
	private CallableStatement stmt;
	private OracleResultSet rset;
	//-----------------------------------------------
	public database()
		{
		conn = null;
		}
	//-----------------------------------------------
	public void getConn() throws Exception
		{
		Context context = (Context)(new InitialContext().lookup("java:comp/env"));
		DataSource ds = (DataSource)context.lookup("jdbc/tox");
		conn = ds.getConnection();
		}
	//-----------------------------------------------
	public void releaseConn() throws Exception
		{
		conn.close();
		}
	//------------------------------------------------
	public String plsql (String call)
		{
		if (debug.VERBOSE) debug.logger("gov.llnl.tox.util.database","plsql(call)>> "+call);
		String result = "";
		try
			{
			stmt = conn.prepareCall("{ ? = call " + call + "}");
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			//----------------------------------------
			try
				{
				stmt.execute();
				rset = (OracleResultSet)stmt.getObject(1);
				//------------------------------------
				if (rset.next())
					{
					int key = rset.getInt("KEY");
					StringBuilder resultBuffer = new StringBuilder(512);
					do
						{
						resultBuffer.append(rset.getString("TXT"));
						}
					while (rset.next());
					result = resultBuffer.toString();
					//------------------------------------
					Statement clean = conn.createStatement();
					try
						{
						clean.executeUpdate("delete from spool where key = " + key);
						conn.commit();
						clean.close();
						}
					catch(Exception e)
						{
						result = debug.logger("gov.llnl.tox.util.database","error: plsql:clean>> ",e);
						}
					finally
						{
						try
							{
							clean.close();
							}
						catch(Exception e)
							{
							debug.logger("gov.llnl.tox.util.database","error: plsql:finally>> ",e);
							}
						}
					}
				}
			catch(Exception e)
				{
				result = debug.logger("gov.llnl.tox.util.database","error: plsql:execute>> " + call,e);
				}
			finally
				{
				try
					{
					rset.close();
					stmt.close();
					}
				catch(Exception e)
					{
					debug.logger("gov.llnl.tox.util.database","error: plsql:finally>> ",e);
					}
				}
			}
		catch(SQLException e)
			{
			result = debug.logger("gov.llnl.tox.util.database","error: plsql:prepareCall>> " + call,e);
			}
		if (debug.VERBOSE) debug.logger("gov.llnl.tox.util.database","plsql(output)>> "+result);
		return(result);
		}
	//-----------------------------------------------
	}
//----------------------------------------------------
