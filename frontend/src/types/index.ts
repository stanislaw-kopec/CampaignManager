export interface Campaign {
  id: number;
  campaignName: string;
  keywords: string[];
  bidAmount: number;
  campaignFund: number;
  status: 'ON' | 'OFF';
  town: string;
  radius: number;
  username: string;
}

export interface CampaignRequest {
  campaignName: string;
  keywordIds: number[];
  bidAmount: number;
  campaignFund: number;
  status: 'ON' | 'OFF';
  townId: number;
  radius: number;
  userId: number;
}

export interface User {
  id: number;
  username: string;
  hasEmeraldAccount: boolean;
  emeraldBalance: number | null;
  campaignCount: number;
}

export interface TopUpResponse {
  userId: number;
  username: string;
  amountAdded: number;
  balanceBefore: number;
  balanceAfter: number;
  transactionTime: string;
}

export interface Keyword {
  id: number;
  name: string;
}

export interface Town {
  id: number;
  name: string;
}