import { useModal } from '../shared/modal-provider';
import { BaseModal } from '../shared/base-modal';
import { Input } from '../ui/input';
import { Label } from '../ui/label';
import { Button } from '../ui/button';
import { Switch } from '../ui/switch';
import { Textarea } from '../ui/textarea';
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from '../ui/command';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '../ui/popover';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '../ui/select';
import { Calendar } from '../ui/calendar';
import { CalendarIcon, Check, ChevronsUpDown, User, Baby } from 'lucide-react';
import { format } from 'date-fns';
import { fr } from 'date-fns/locale';
import { cn } from '@/lib/utils';
import { useState } from 'react';
import { useNotification } from '../shared/app-notification';

interface Patient {
  id: string;
  nom: string;
  prenom: string;
  dateNaissance: string;
  age: number;
  telephone: string;
  email: string;
  adresse: string;
  sexe: 'M' | 'F';
  typePatient: 'adulte' | 'enfant';
  parentId?: string;
  notes?: string;
}

// Données simulées des parents existants
const parentsData: Patient[] = [
  {
    id: '1',
    nom: 'Dupont',
    prenom: 'Marie',
    dateNaissance: '1985-03-15',
    age: 40,
    telephone: '01 23 45 67 89',
    email: 'marie.dupont@email.com',
    adresse: '123 Rue de la Santé, Paris',
    sexe: 'F',
    typePatient: 'adulte'
  },
  {
    id: '2',
    nom: 'Martin',
    prenom: 'Pierre',
    dateNaissance: '1982-07-22',
    age: 43,
    telephone: '01 98 76 54 32',
    email: 'pierre.martin@email.com',
    adresse: '456 Avenue du Bien-être, Lyon',
    sexe: 'M',
    typePatient: 'adulte'
  },
  {
    id: '3',
    nom: 'Bernard',
    prenom: 'Sophie',
    dateNaissance: '1990-11-08',
    age: 34,
    telephone: '01 55 44 33 22',
    email: 'sophie.bernard@email.com',
    adresse: '789 Boulevard de la Paix, Marseille',
    sexe: 'F',
    typePatient: 'adulte'
  }
];

export function CreatePatientModal() {
  const { closeModal } = useModal();
  const notification = useNotification();
  const [formData, setFormData] = useState<Partial<Patient>>({
    typePatient: 'adulte',
    sexe: 'M'
  });
  const [selectedDate, setSelectedDate] = useState<Date>();
  const [openParentCombo, setOpenParentCombo] = useState(false);
  const [selectedParent, setSelectedParent] = useState<Patient | null>(null);

  const modalId = 'create-patient';

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Validation des champs obligatoires
    if (!formData.nom || !formData.prenom || !selectedDate || !formData.telephone || !formData.adresse) {
      notification.validation("Veuillez remplir tous les champs obligatoires");
      return;
    }

    // Validation spécifique pour les enfants
    if (formData.typePatient === 'enfant' && !selectedParent) {
      notification.validation("Veuillez sélectionner un parent pour ce patient enfant");
      return;
    }

    // Validation de l'âge pour les enfants
    if (formData.typePatient === 'enfant' && selectedDate) {
      const age = new Date().getFullYear() - selectedDate.getFullYear();
      if (age >= 18) {
        notification.warning({
          title: "Âge incohérent",
          description: "Un patient de plus de 18 ans devrait être enregistré comme adulte"
        });
        return;
      }
    }

    // Validation du format téléphone (simple)
    const phoneRegex = /^[0-9\s\-\+\(\)]+$/;
    if (!phoneRegex.test(formData.telephone)) {
      notification.error({
        title: "Format téléphone invalide",
        description: "Veuillez vérifier le numéro de téléphone"
      });
      return;
    }

    // Validation de l'email si fourni
    if (formData.email && !/\S+@\S+\.\S+/.test(formData.email)) {
      notification.error({
        title: "Format email invalide",
        description: "Veuillez vérifier l'adresse email"
      });
      return;
    }

    // Simulation de l'enregistrement
    const loadingId = notification.loading({
      title: "Création du patient en cours...",
      description: "Enregistrement des informations"
    });

    // Préparer les données finales
    const finalData = {
      ...formData,
      id: Date.now().toString(),
      dateNaissance: selectedDate ? format(selectedDate, 'yyyy-MM-dd') : '',
      age: selectedDate ? new Date().getFullYear() - selectedDate.getFullYear() : 0,
      parentId: selectedParent?.id,
    };

    // Simuler un appel API
    setTimeout(() => {
      notification.updateLoading(loadingId, {
        type: 'success',
        title: "Patient créé avec succès",
        description: `${finalData.prenom} ${finalData.nom} a été ajouté${finalData.typePatient === 'enfant' ? '(e)' : ''} à la base de données`,
      });

      console.log('Création du patient:', finalData);
      closeModal();
      
      // Reset form
      setFormData({ typePatient: 'adulte', sexe: 'M' });
      setSelectedDate(undefined);
      setSelectedParent(null);
    }, 1500);
  };

  const handleInputChange = (field: keyof Patient, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleDateChange = (date: Date | undefined) => {
    setSelectedDate(date);
    if (date) {
      const age = new Date().getFullYear() - date.getFullYear();
      setFormData(prev => ({ 
        ...prev, 
        dateNaissance: format(date, 'yyyy-MM-dd'),
        age 
      }));
    }
  };

  const handleTypeChange = (isEnfant: boolean) => {
    setFormData(prev => ({ 
      ...prev, 
      typePatient: isEnfant ? 'enfant' : 'adulte' 
    }));
    if (!isEnfant) {
      setSelectedParent(null);
    }
  };

  const handleParentSelect = (parent: Patient) => {
    setSelectedParent(parent);
    setOpenParentCombo(false);
  };

  return (
    <BaseModal
      modalId={modalId}
      title="Nouveau patient"
      description="Créer un nouveau patient (adulte ou enfant)"
      showFooter={false}
      size="lg"
    >
      <form onSubmit={handleSubmit} className="space-y-6">
        {/* Switch Type de Patient */}
        <div className="flex items-center justify-between p-4 bg-muted/30 rounded-lg border">
          <div className="flex items-center space-x-3">
            <User className="w-5 h-5 text-primary" />
            <div>
              <div className="font-medium">Type de patient</div>
              <div className="text-sm text-muted-foreground">
                {formData.typePatient === 'enfant' ? 'Patient mineur' : 'Patient adulte'}
              </div>
            </div>
          </div>
          <div className="flex items-center space-x-3">
            <Label htmlFor="type-switch" className="text-sm font-medium">
              Adulte
            </Label>
            <Switch
              id="type-switch"
              checked={formData.typePatient === 'enfant'}
              onCheckedChange={handleTypeChange}
            />
            <Label htmlFor="type-switch" className="text-sm font-medium flex items-center space-x-1">
              <Baby className="w-4 h-4" />
              <span>Enfant</span>
            </Label>
          </div>
        </div>

        {/* Informations personnelles */}
        <div className="grid grid-cols-2 gap-4">
          <div className="space-y-2">
            <Label htmlFor="nom">Nom *</Label>
            <Input
              id="nom"
              value={formData.nom || ''}
              onChange={(e) => handleInputChange('nom', e.target.value)}
              placeholder="Nom de famille"
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="prenom">Prénom *</Label>
            <Input
              id="prenom"
              value={formData.prenom || ''}
              onChange={(e) => handleInputChange('prenom', e.target.value)}
              placeholder="Prénom"
              required
            />
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div className="space-y-2">
            <Label htmlFor="dateNaissance">Date de naissance *</Label>
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className={cn(
                    "w-full justify-start text-left font-normal",
                    !selectedDate && "text-muted-foreground"
                  )}
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {selectedDate ? (
                    format(selectedDate, "PPP", { locale: fr })
                  ) : (
                    <span>Sélectionner une date</span>
                  )}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0" align="start">
                <Calendar
                  mode="single"
                  selected={selectedDate}
                  onSelect={handleDateChange}
                  disabled={(date) =>
                    date > new Date() || date < new Date("1900-01-01")
                  }
                  initialFocus
                />
              </PopoverContent>
            </Popover>
          </div>
          <div className="space-y-2">
            <Label htmlFor="sexe">Sexe *</Label>
            <Select
              value={formData.sexe || 'M'}
              onValueChange={(value) => handleInputChange('sexe', value)}
            >
              <SelectTrigger>
                <SelectValue placeholder="Sélectionner le sexe" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="M">Masculin</SelectItem>
                <SelectItem value="F">Féminin</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        {/* Sélection du parent si c'est un enfant */}
        {formData.typePatient === 'enfant' && (
          <div className="space-y-2">
            <Label htmlFor="parent">Parent/Tuteur légal *</Label>
            <Popover open={openParentCombo} onOpenChange={setOpenParentCombo}>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  role="combobox"
                  aria-expanded={openParentCombo}
                  className="w-full justify-between"
                >
                  {selectedParent ? (
                    <span className="flex items-center space-x-2">
                      <User className="w-4 h-4" />
                      <span>
                        {selectedParent.prenom} {selectedParent.nom} ({selectedParent.telephone})
                      </span>
                    </span>
                  ) : (
                    "Sélectionner un parent..."
                  )}
                  <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-full p-0">
                <Command>
                  <CommandInput placeholder="Rechercher un parent..." />
                  <CommandList>
                    <CommandEmpty>Aucun parent trouvé.</CommandEmpty>
                    <CommandGroup>
                      {parentsData.map((parent) => (
                        <CommandItem
                          key={parent.id}
                          value={`${parent.prenom} ${parent.nom} ${parent.telephone}`}
                          onSelect={() => handleParentSelect(parent)}
                        >
                          <Check
                            className={cn(
                              "mr-2 h-4 w-4",
                              selectedParent?.id === parent.id ? "opacity-100" : "opacity-0"
                            )}
                          />
                          <div className="flex items-center space-x-2">
                            <User className="w-4 h-4 text-muted-foreground" />
                            <div>
                              <div className="font-medium">
                                {parent.prenom} {parent.nom}
                              </div>
                              <div className="text-sm text-muted-foreground">
                                {parent.telephone} • {parent.email}
                              </div>
                            </div>
                          </div>
                        </CommandItem>
                      ))}
                    </CommandGroup>
                  </CommandList>
                </Command>
              </PopoverContent>
            </Popover>
          </div>
        )}

        {/* Contact et adresse */}
        <div className="grid grid-cols-2 gap-4">
          <div className="space-y-2">
            <Label htmlFor="telephone">Téléphone *</Label>
            <Input
              id="telephone"
              value={formData.telephone || ''}
              onChange={(e) => handleInputChange('telephone', e.target.value)}
              placeholder="01 23 45 67 89"
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              type="email"
              value={formData.email || ''}
              onChange={(e) => handleInputChange('email', e.target.value)}
              placeholder="email@exemple.com"
            />
          </div>
        </div>

        <div className="space-y-2">
          <Label htmlFor="adresse">Adresse complète *</Label>
          <Input
            id="adresse"
            value={formData.adresse || ''}
            onChange={(e) => handleInputChange('adresse', e.target.value)}
            placeholder="Numéro, rue, ville, code postal"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="notes">Notes médicales (optionnel)</Label>
          <Textarea
            id="notes"
            value={formData.notes || ''}
            onChange={(e) => handleInputChange('notes', e.target.value)}
            placeholder="Allergies, conditions médicales, informations importantes..."
            rows={3}
          />
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button type="button" variant="outline" onClick={() => closeModal()}>
            Annuler
          </Button>
          <Button type="submit">
            Créer le patient
          </Button>
        </div>
      </form>
    </BaseModal>
  );
}
