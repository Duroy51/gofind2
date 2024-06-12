import dayjs from 'dayjs';
import { ITrajet } from 'app/shared/model/trajet.model';
import { ILocation } from 'app/shared/model/location.model';
import { IDeclarationVol } from 'app/shared/model/declaration-vol.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IAnnonce {
  id?: number;
  titre?: string | null;
  description?: string | null;
  type?: string | null;
  statut?: string | null;
  datecreation?: dayjs.Dayjs | null;
  trajet?: ITrajet | null;
  location?: ILocation | null;
  declarationVol?: IDeclarationVol | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<IAnnonce> = {};
