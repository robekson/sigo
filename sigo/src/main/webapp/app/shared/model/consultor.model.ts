import { Moment } from 'moment';

export interface IConsultor {
  id?: number;
  nome?: string;
  cnpj?: string;
  dataContratacao?: Moment;
  email?: string;
  telefone?: string;
}

export class Consultor implements IConsultor {
  constructor(
    public id?: number,
    public nome?: string,
    public cnpj?: string,
    public dataContratacao?: Moment,
    public email?: string,
    public telefone?: string
  ) {}
}
