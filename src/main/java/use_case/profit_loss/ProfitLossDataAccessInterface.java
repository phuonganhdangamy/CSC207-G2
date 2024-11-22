package use_case.profit_loss;

import entity.Portfolio;

public interface ProfitLossDataAccessInterface {
    Portfolio getPortfolio(String userId);
}
