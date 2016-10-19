package scheduler;

import dao.FillsDao;
import org.joda.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import util.OrderUpdater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimeZone;

public class Tasks {

	private FillsDao fillsDao;
	private JdbcTemplate jdbcTemplate;
	private String path;

	public Tasks(FillsDao fillsDao, JdbcTemplate jdbcTemplate, String path) {
		this.fillsDao = fillsDao;
		this.jdbcTemplate = jdbcTemplate;
		this.path = path;
	}

	// perform every 30 minutes
	@Scheduled(cron = "0 */30 * * * ?")
//	@Scheduled(cron = "0 48 5 * * ?")
	public void updateOrders() {
		OrderUpdater mu = new OrderUpdater(fillsDao, path);
		System.out.println("Start updating orders");
		mu.perform();
		String logData = "Mail update finished : " + new LocalDateTime() + " " + TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT)
				+ " \tOrders added : " + mu.getOrdersCount();
		log(logData);
		System.out.println("End updating orders");
	}

	// database log
	private void log(String info) {
		try (Connection connection = jdbcTemplate.getDataSource().getConnection();) {
			String query = "INSERT INTO amirus_log (log_data) VALUES (?);";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, info);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
