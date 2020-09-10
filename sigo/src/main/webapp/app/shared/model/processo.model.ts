import { ITipoProcesso } from 'app/shared/model/tipo-processo.model';
import { StatusProcesso } from 'app/shared/model/enumerations/status-processo.model';
import { Etapa } from 'app/shared/model/enumerations/etapa.model';

export interface IProcesso {
  id?: number;
  idMateriaPrima?: number;
  idProduto?: number;
  nome?: string;
  descricao?: string;
  status?: StatusProcesso;
  etapa?: Etapa;
  processoId?: number;
  tipoProcessos?: ITipoProcesso[];
  processoFilhoId?: number;
}

export class Processo implements IProcesso {
  constructor(
    public id?: number,
    public idMateriaPrima?: number,
    public idProduto?: number,
    public nome?: string,
    public descricao?: string,
    public status?: StatusProcesso,
    public etapa?: Etapa,
    public processoId?: number,
    public tipoProcessos?: ITipoProcesso[],
    public processoFilhoId?: number
  ) {}
}
